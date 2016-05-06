package ext.cds2.modules

import nasa.nccs.cdapi.cdm._
import nasa.nccs.cdapi.kernels._
import nasa.nccs.cdapi.tensors.CDFloatArray
import nasa.nccs.esgf.process._


class ModuleTemplate extends KernelModule {
  override val version = "1.0-SNAPSHOT"
  override val organization = "nasa.nccs"
  override val author = "Thomas Maxwell"
  override val contact = "thomas.maxwell@nasa.gov"

  class max extends Kernel {
    val inputs = List(Port("input fragment", "1"))
    val outputs = List(Port("result", "1"))
    override val description = "Maximum over Axes on Input Fragment"

    def execute(operationCx: OperationContext, requestCx: RequestContext, serverCx: ServerContext): ExecutionResult = {
      val inputVar: KernelDataInput = inputVars(operationCx, requestCx, serverCx).head
      val input_array: CDFloatArray = inputVar.dataFragment.data
      val axisSpecs = inputVar.axisIndices
      val async = requestCx.config("async", "false").toBoolean
      val axes = axisSpecs.getAxes
      val t10 = System.nanoTime
      val max_val_masked: CDFloatArray = input_array.max(axes.toArray)
      val t11 = System.nanoTime
      logger.info("Max_val_masked, time = %.4f s, result = %s".format((t11 - t10) / 1.0E9, max_val_masked.toString))
      val variable = serverCx.getVariable(inputVar.getSpec)
      val section = inputVar.getSpec.getReducedSection(Set(axes: _*))
      if (async) {
        new AsyncExecutionResult(saveResult(max_val_masked, requestCx, serverCx, variable.getGridSpec(section), inputVar.getVariableMetadata(serverCx), inputVar.getDatasetMetadata(serverCx)))
      }
      else new BlockingExecutionResult(operationCx.identifier, List(inputVar.getSpec), variable.getGridSpec(section), max_val_masked)
    }
  }
}


object modTest extends App {
  val mod = new ModuleTemplate()
  val km = mod.kernelMap
  println( mod.toXml.toString )
}
