package ext.cds2.modules

import nasa.nccs.cdapi.cdm.{BinnedArrayFactory, KernelDataInput, PartitionedFragment, aveSliceAccumulator}
import nasa.nccs.cdapi.kernels._
import nasa.nccs.cdapi.tensors.Nd4jMaskedTensor
import nasa.nccs.esgf.process.{OperationContext, RequestContext, ServerContext}
import org.nd4j.linalg.factory.Nd4j

class ModuleTemplate extends KernelModule {
  override val version = "1.0-SNAPSHOT"
  override val organization = "nasa.nccs"
  override val author = "Thomas Maxwell"
  override val contact = "thomas.maxwell@nasa.gov"

  class average extends Kernel {
    val inputs = List(Port("input fragment", "1"))
    val outputs = List(Port("result", "1"))
    override val description = "Average over Input Fragment"

    def execute( operationCx: OperationContext, requestCx: RequestContext, serverCx: ServerContext ): ExecutionResult = {
      val inputVar: KernelDataInput  =  inputVars( operationCx, requestCx, serverCx ).head
      val input_array = inputVar.dataFragment
      val axes = inputVar.axisIndices.getAxes
      val variable = serverCx.getVariable( inputVar.getSpec )
      val section = inputVar.getSpec.getSubSection(input_array.fragmentSpec.roi)
      val result = "0.0"  // Add computation here
      logger.info("Kernel %s: Executed operation %s, result = %s ".format( name, operation, result ))
      new BlockingExecutionResult( operationCx.identifier, List(inputVar.getSpec), variable.getGridSpec(section),  new Nd4jMaskedTensor(Nd4j.zeros(0),Float.MaxValue) )
    }
  }
}


object modTest extends App {
  val mod = new ModuleTemplate()
  val km = mod.kernelMap
  println( mod.toXml.toString )
}
