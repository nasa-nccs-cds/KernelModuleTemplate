package ext.cds2.modules

import nasa.nccs.cdapi.kernels.{ KernelModule, Kernel, ExecutionResult, Port, DataFragment }

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

    def execute(inputSubsets: List[DataFragment], run_args: Map[String, Any]): ExecutionResult = {
      val inputSubset = inputSubsets.head
      val result = "0.0"  // Add computation here
      logger.info("Kernel %s: Executed operation %s, result = %s ".format( name, operation, result ))
      new ExecutionResult(Array.emptyFloatArray)
    }
  }
}


object modTest extends App {
  val mod = new ModuleTemplate()
  val km = mod.kernelMap
  println( mod.toXml.toString )
}
