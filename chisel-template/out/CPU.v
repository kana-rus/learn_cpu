module Core(
  input         clock,
  input         reset,
  output [31:0] io_imem_addr,
  input  [31:0] io_imem_inst,
  output        io_exit
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
`endif // RANDOMIZE_REG_INIT
  reg [31:0] pc_reg; // @[Core.scala 23:25]
  wire [31:0] _pc_reg_T_1 = pc_reg + 32'h4; // @[Core.scala 24:22]
  wire  _T_1 = ~reset; // @[Core.scala 33:11]
  assign io_imem_addr = pc_reg; // @[Core.scala 26:18]
  assign io_exit = io_imem_inst == 32'h34333231; // @[Core.scala 29:22]
  always @(posedge clock) begin
    if (reset) begin // @[Core.scala 23:25]
      pc_reg <= 32'h0; // @[Core.scala 23:25]
    end else begin
      pc_reg <= _pc_reg_T_1; // @[Core.scala 24:12]
    end
    `ifndef SYNTHESIS
    `ifdef PRINTF_COND
      if (`PRINTF_COND) begin
    `endif
        if (~reset) begin
          $fwrite(32'h80000002,"pc_reg : 0x%x\n",pc_reg); // @[Core.scala 33:11]
        end
    `ifdef PRINTF_COND
      end
    `endif
    `endif // SYNTHESIS
    `ifndef SYNTHESIS
    `ifdef PRINTF_COND
      if (`PRINTF_COND) begin
    `endif
        if (_T_1) begin
          $fwrite(32'h80000002,"inst   : 0x%x\n",io_imem_inst); // @[Core.scala 34:11]
        end
    `ifdef PRINTF_COND
      end
    `endif
    `endif // SYNTHESIS
    `ifndef SYNTHESIS
    `ifdef PRINTF_COND
      if (`PRINTF_COND) begin
    `endif
        if (_T_1) begin
          $fwrite(32'h80000002,"---------\n"); // @[Core.scala 35:11]
        end
    `ifdef PRINTF_COND
      end
    `endif
    `endif // SYNTHESIS
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  pc_reg = _RAND_0[31:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
module Memory(
  input         clock,
  input  [31:0] io_imem_addr,
  output [31:0] io_imem_inst
);
`ifdef RANDOMIZE_MEM_INIT
  reg [31:0] _RAND_0;
`endif // RANDOMIZE_MEM_INIT
  reg [7:0] mem [0:16383]; // @[Memory.scala 18:18]
  wire  mem_io_imem_inst_MPORT_en; // @[Memory.scala 18:18]
  wire [13:0] mem_io_imem_inst_MPORT_addr; // @[Memory.scala 18:18]
  wire [7:0] mem_io_imem_inst_MPORT_data; // @[Memory.scala 18:18]
  wire  mem_io_imem_inst_MPORT_1_en; // @[Memory.scala 18:18]
  wire [13:0] mem_io_imem_inst_MPORT_1_addr; // @[Memory.scala 18:18]
  wire [7:0] mem_io_imem_inst_MPORT_1_data; // @[Memory.scala 18:18]
  wire  mem_io_imem_inst_MPORT_2_en; // @[Memory.scala 18:18]
  wire [13:0] mem_io_imem_inst_MPORT_2_addr; // @[Memory.scala 18:18]
  wire [7:0] mem_io_imem_inst_MPORT_2_data; // @[Memory.scala 18:18]
  wire  mem_io_imem_inst_MPORT_3_en; // @[Memory.scala 18:18]
  wire [13:0] mem_io_imem_inst_MPORT_3_addr; // @[Memory.scala 18:18]
  wire [7:0] mem_io_imem_inst_MPORT_3_data; // @[Memory.scala 18:18]
  wire [31:0] _io_imem_inst_T_1 = io_imem_addr + 32'h3; // @[Memory.scala 23:26]
  wire [31:0] _io_imem_inst_T_4 = io_imem_addr + 32'h2; // @[Memory.scala 24:26]
  wire [31:0] _io_imem_inst_T_7 = io_imem_addr + 32'h1; // @[Memory.scala 25:26]
  wire [15:0] io_imem_inst_lo = {mem_io_imem_inst_MPORT_2_data,mem_io_imem_inst_MPORT_3_data}; // @[Cat.scala 33:92]
  wire [15:0] io_imem_inst_hi = {mem_io_imem_inst_MPORT_data,mem_io_imem_inst_MPORT_1_data}; // @[Cat.scala 33:92]
  assign mem_io_imem_inst_MPORT_en = 1'h1;
  assign mem_io_imem_inst_MPORT_addr = _io_imem_inst_T_1[13:0];
  assign mem_io_imem_inst_MPORT_data = mem[mem_io_imem_inst_MPORT_addr]; // @[Memory.scala 18:18]
  assign mem_io_imem_inst_MPORT_1_en = 1'h1;
  assign mem_io_imem_inst_MPORT_1_addr = _io_imem_inst_T_4[13:0];
  assign mem_io_imem_inst_MPORT_1_data = mem[mem_io_imem_inst_MPORT_1_addr]; // @[Memory.scala 18:18]
  assign mem_io_imem_inst_MPORT_2_en = 1'h1;
  assign mem_io_imem_inst_MPORT_2_addr = _io_imem_inst_T_7[13:0];
  assign mem_io_imem_inst_MPORT_2_data = mem[mem_io_imem_inst_MPORT_2_addr]; // @[Memory.scala 18:18]
  assign mem_io_imem_inst_MPORT_3_en = 1'h1;
  assign mem_io_imem_inst_MPORT_3_addr = io_imem_addr[13:0];
  assign mem_io_imem_inst_MPORT_3_data = mem[mem_io_imem_inst_MPORT_3_addr]; // @[Memory.scala 18:18]
  assign io_imem_inst = {io_imem_inst_hi,io_imem_inst_lo}; // @[Cat.scala 33:92]
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_MEM_INIT
  _RAND_0 = {1{`RANDOM}};
  for (initvar = 0; initvar < 16384; initvar = initvar+1)
    mem[initvar] = _RAND_0[7:0];
`endif // RANDOMIZE_MEM_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
module CPU(
  input   clock,
  input   reset,
  output  io_exit
);
  wire  core_clock; // @[CPU.scala 15:22]
  wire  core_reset; // @[CPU.scala 15:22]
  wire [31:0] core_io_imem_addr; // @[CPU.scala 15:22]
  wire [31:0] core_io_imem_inst; // @[CPU.scala 15:22]
  wire  core_io_exit; // @[CPU.scala 15:22]
  wire  memory_clock; // @[CPU.scala 16:24]
  wire [31:0] memory_io_imem_addr; // @[CPU.scala 16:24]
  wire [31:0] memory_io_imem_inst; // @[CPU.scala 16:24]
  Core core ( // @[CPU.scala 15:22]
    .clock(core_clock),
    .reset(core_reset),
    .io_imem_addr(core_io_imem_addr),
    .io_imem_inst(core_io_imem_inst),
    .io_exit(core_io_exit)
  );
  Memory memory ( // @[CPU.scala 16:24]
    .clock(memory_clock),
    .io_imem_addr(memory_io_imem_addr),
    .io_imem_inst(memory_io_imem_inst)
  );
  assign io_exit = core_io_exit; // @[CPU.scala 19:13]
  assign core_clock = clock;
  assign core_reset = reset;
  assign core_io_imem_inst = memory_io_imem_inst; // @[CPU.scala 18:18]
  assign memory_clock = clock;
  assign memory_io_imem_addr = core_io_imem_addr; // @[CPU.scala 18:18]
endmodule
