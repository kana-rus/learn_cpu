package `04_sw`

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import common.Consts._

class ImemPortIO extends Bundle {
    val addr = Input(UInt(WORD_LEN.W))
    val inst = Output(UInt(WORD_LEN.W))
}

class DmemPortIO extends Bundle {
    val addr  = Input(UInt(WORD_LEN.W))
    val rdata = Output(UInt(WORD_LEN.W))

    val wen = Input(Bool())             // + : 書き込み可否信号
    val wdata = Input(UInt(WORD_LEN.W)) // + : 書き込みデータ
}

class Memory extends Module {
    val io = IO(new Bundle {
        val imem = new ImemPortIO()
        val dmem = new DmemPortIO()
    })

    val mem = Mem(16384, UInt(8.W))

    loadMemoryFromFile(mem, "src/hex/sw.hex") // change

    io.imem.inst := Cat(
        mem(io.imem.addr + 3.U(WORD_LEN.W)),
        mem(io.imem.addr + 2.U(WORD_LEN.W)),
        mem(io.imem.addr + 1.U(WORD_LEN.W)),
        mem(io.imem.addr),
    )

    // load data to io.dmem.rdata
    io.dmem.rdata := Cat(
        mem(io.dmem.addr + 3.U(WORD_LEN.W)),
        mem(io.dmem.addr + 2.U(WORD_LEN.W)), 
        mem(io.dmem.addr + 1.U(WORD_LEN.W)),
        mem(io.dmem.addr)
    )

    // write wdata to io.dmem.wdata by bytes when `wen` is true
    when(io.dmem.wen) {
        mem(io.dmem.addr)       := io.dmem.wdata( 7, 0)
        mem(io.dmem.addr + 1.U) := io.dmem.wdata(15, 8)
        mem(io.dmem.addr + 2.U) := io.dmem.wdata(23,16)
        mem(io.dmem.addr + 3.U) := io.dmem.wdata(31,24)
    }
}
