package ext.cds2.modules

import nasa.nccs.cdapi.cdm.{BinnedArrayFactory, KernelDataInput, PartitionedFragment, aveSliceAccumulator}
import nasa.nccs.cdapi.kernels._
import nasa.nccs.cdapi.tensors.Nd4jMaskedTensor
import org.nd4j.linalg.factory.Nd4j

/**
  * Created by tpmaxwel on 2/9/16.
  */
class ModuleTemplate extends KernelModule {
  override val version = "1.0-SNAPSHOT"
  override val organization = "nasa.nccs"
  override val author = "Thomas Maxwell"
  override val contact = "thomas.maxwell@nasa.gov"

  class average extends Kernel {
    val inputs = List(Port("input fragment", "1"))
    val outputs = List(Port("result", "1"))
    override val description = "Average over Input Fragment"

    def execute(context: ExecutionContext ): ExecutionResult = {
      val inputVar: KernelDataInput  =  context.inputs.head
      val optargs: Map[String,String] =  context.getConfiguration("operation")
      val input_array = inputVar.dataFragment
      val axisSpecs = inputVar.axisIndices
      val axes = axisSpecs.getAxes
      val variable = context.dataManager.getVariable( inputVar.getSpec )
      val section = inputVar.getSpec.getSubSection(input_array.fragmentSpec.roi)
      val result = "0.0"  // Add computation here
      logger.info("Kernel %s: Executed operation %s, result = %s ".format( name, operation, result ))
      new BlockingExecutionResult(context.id, List(inputVar.getSpec), variable.getGridSpec(section),  new Nd4jMaskedTensor(Nd4j.zeros(0),Float.MaxValue) )
    }
  }
}


object modTest extends App {
  val mod = new ModuleTemplate()
  val km = mod.kernelMap
  println( mod.toXml.toString )
}
