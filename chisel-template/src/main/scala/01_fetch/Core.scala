package fetch

import chisel3._
import chisel3.util._
import common.Consts._

class Core extends Module {
    val io = IO(new Bundle {
        // val imem = Flipped(new ImemportIO)
        val imem = new Bundle {
            val addr = Output(UInt(WORD_LEN.W))
            val inst = Input(UInt(WORD_LEN.W))
        }

        val exit = Output(Bool())
    })

    val regfile = Mem(32, UInt(WORD_LEN.W))




    val pc_reg = RegInit(START_ADDR)
    pc_reg := pc_reg + 4.U(WORD_LEN.W)

    io.imem.addr := pc_reg
    val inst = io.imem.inst

    io.exit := (inst === 0x34333231.U(WORD_LEN.W))


    // for debug
    printf(p"pc_reg : 0x${Hexadecimal(pc_reg)}\n")
    printf(p"inst   : 0x${Hexadecimal(inst)}\n")
    printf("---------\n")
}