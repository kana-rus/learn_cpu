package fetch

import chisel3._
import chisel3.util._

class Top extends Module {
    val io = IO(new Bundle {
        val exit = Output(Bool())
    })

    val core = Module(new Core())
    val memory = Module(new Memory())

    // core.io.imem <> memory.io.imem
    core.io.imem.addr := memory.io.imem.addr
    core.io.imem.inst := memory.io.imem.inst

    io.exit := core.io.exit
}