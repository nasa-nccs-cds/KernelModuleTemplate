package ext.cds2.modules

import nasa.nccs.cdapi.cdm.{KernelDataInput, BinnedArrayFactory, aveSliceAccumulator, PartitionedFragment}
import nasa.nccs.cdapi.kernels._

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
      val inputSubsets: List[KernelDataInput] =  context.fragments
      val optargs: Map[String,String] =  context.args
      val input_array = inputSubsets(0).dataFragment.data
      val axisSpecs = inputSubsets(0).axisSpecs
      val axes = axisSpecs.getAxes
      val inputSubset = inputSubsets.head
      val result = "0.0"  // Add computation here
      logger.info("Kernel %s: Executed operation %s, result = %s ".format( name, operation, result ))
      new BlockingExecutionResult(Array.emptyFloatArray)
    }
  }
}


object modTest extends App {
  val mod = new ModuleTemplate()
  val km = mod.kernelMap
  println( mod.toXml.toString )
}
