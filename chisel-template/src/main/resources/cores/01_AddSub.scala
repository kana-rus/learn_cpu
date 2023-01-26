package addsub

import chisel3._
import chisel3.util._
import common.Consts._
import common.Instructions.{LW, SW, ADDI, ADD, SUB}

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
            imm_i(11) // : imm_i の 11 ビット目 つまり 最上位ビット (0 or 1)
        ),
        imm_i
    )

    // s 形式の即値や rd / rs2 の位置を変えないために 
    // ２箇所に分かれている
    val imm_s = Cat(
        inst(31,25),
        inst(11,7)
    )
    val imm_s_sext = Cat(
        Fill(20, imm_s(11)),
        imm_s
    )


    // ====================================
    // Execute (EX) stage
    val alu_out = MuxCase(0.U(WORD_LEN.W), Seq(
        (inst === LW || inst === ADDI) -> (rs1_data + imm_i_sext), // change
        (inst === SW)                  -> (rs1_data + imm_s_sext),
        (inst === ADD)                 -> (rs1_data + rs2_data),
        (inst === SUB)                 -> (rs1_data - rs2_data),
    ))


    // ====================================
    // Memory Access (MEM) stage
    io.dmem.addr  := alu_out

    io.dmem.wen   := (inst === SW)
    io.dmem.wdata := rs2_data


    // ====================================
    // Writeback (WB) stage
    val wb_data = io.dmem.rdata
    when(inst === LW || inst === ADD || inst === ADDI || inst === SUB) { // add conditions
        regfile(rd_addr) := wb_data
    }


    // ====================================
    // debug
    io.exit := (inst === 0x22222222.U(WORD_LEN.W))

    printf(p"pc_reg     : 0x${Hexadecimal(pc_reg)}\n")
    printf(p"inst       : 0x${Hexadecimal(inst)}\n")
    printf(p"rs1_addr   : $rs1_addr\n")
    printf(p"rs2_addr   : $rs2_addr\n")
    printf(p"rd_addr    : $rd_addr\n")
    printf(p"rs1_data   : 0x${Hexadecimal(rs1_data)}\n")
    printf(p"rs2_data   : 0x${Hexadecimal(rs2_data)}\n")
    printf(p"wb_data    : 0x${Hexadecimal(wb_data)}\n")
    printf(p"dmem.addr  :${io.dmem.addr}\n")          
    printf(p"dmem.wen   : ${io.dmem.wen}\n")
    printf(p"dmem.wdata : 0x${Hexadecimal(io.dmem.wdata)}\n")
    printf("---------\n")
}
