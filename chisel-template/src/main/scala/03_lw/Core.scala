package `03_lw`

import chisel3._
import chisel3.util._
import common.Consts._
import common.Instructions.LW

class Core extends Module {
    val io = IO(new Bundle {
        val imem = Flipped(new ImemPortIO())
        val dmem = Flipped(new DmemPortIO()) // +
        val exit = Output(Bool())
    })

    val regfile = Mem(32, UInt(WORD_LEN.W))


    // ====================================
    // Instraction Fetch (IF) stage
    val pc_reg = RegInit(START_ADDR)
    pc_reg := pc_reg + 4.U(WORD_LEN.W)

    io.imem.addr := pc_reg
    val inst = io.imem.inst


    // ====================================
    // Instraction Decode (ID) stage
    val rs1_addr = inst(19,15)
    val rs1_data = Mux(
        rs1_addr =/= 0.U(WORD_LEN.U),
        regfile(rs1_addr),
        0.U(WORD_LEN.W)
    )

    val rs2_addr = inst(24,20)
    val rs2_data = Mux(
        rs2_addr =/= 0.U(WORD_LEN.U),
        regfile(rs2_addr),
        0.U(WORD_LEN.W)
    )

    val rd_addr = inst(11,7)

    val imm_i = inst(31,20) // take offset[11:0]
    val imm_i_sext/* "sext" : "sign extended" */ = Cat(
        Fill(20,
            imm_i(11) // : "imm_i の最上位ビット" (0 | 1)
        ),
        imm_i
    )


    // ====================================
    // Execute (EX) stage
    val alu_out = MuxCase(0.U(WORD_LEN.W), Seq(
        (inst === LW) -> (rs1_data + imm_i_sext) // calclate memory address
    ))


    // ====================================
    // Memory Access (MEM) stage
    io.dmem.addr := alu_out


    // ====================================
    // Writeback (WB) stage
    val wb_data = io.dmem.rdata
    when(inst === LW) {
        regfile(rd_addr) := wb_data
    }


    // ====================================
    // debug
    io.exit := (inst === 0x22222222.U(WORD_LEN.W))

    printf(p"pc_reg    : 0x${Hexadecimal(pc_reg)}\n")
    printf(p"inst      : 0x${Hexadecimal(inst)}\n")
    printf(p"rs1_addr  : $rs1_addr\n")
    printf(p"rs2_addr  : $rs2_addr\n")
    printf(p"rd_addr   : $rd_addr\n")
    printf(p"rs1_data  : 0x${Hexadecimal(rs1_data)}\n")
    printf(p"rs2_data  : 0x${Hexadecimal(rs2_data)}\n")
    printf(p"wb_data   : 0x${Hexadecimal(wb_data)}\n") // +
    printf(p"dmem.addr : ${io.dmem.addr}\n")           // +
    printf("---------\n")
}
