package decode

import chisel3._
import chisel3.util._
import common.Consts._

class Core extends Module {
    val io = IO(new Bundle {
        val imem = Flipped(new ImemPortIO())
        val exit = Output(Bool())
    })

    val regfile = Mem(32, UInt(WORD_LEN.W))

    // === Instraction Fetch (IF) stage ===
    val pc_reg = RegInit(START_ADDR)
    pc_reg := pc_reg + 4.U(WORD_LEN.W)

    io.imem.addr := pc_reg
    val inst = io.imem.inst
    // ====================================

    // === Instraction Decode (ID) stage ==
    val rs1_addr = inst(19,15)
    val rs1_data = Mux()

    val rs2_addr = inst(24,20)
    val rd_addr = inst(11,7)

    // ====================================


    // for debug
    printf(p"pc_reg : 0x${Hexadecimal(pc_reg)}\n")
    printf(p"inst   : 0x${Hexadecimal(inst)}\n")
    printf("---------\n")
}
