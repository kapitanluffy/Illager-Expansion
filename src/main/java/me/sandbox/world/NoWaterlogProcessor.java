package me.sandbox.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.Waterloggable;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;

//Credit to TelepathicGrunt for providing the anti-waterlogging processor!!

public class NoWaterlogProcessor extends StructureProcessor {
    public static Codec<NoWaterlogProcessor> CODEC = Codec.unit(NoWaterlogProcessor::new);

    @Override
    public Structure.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData data) {
        ChunkPos currentChunkPos = new ChunkPos(structureBlockInfo2.pos);
        if (structureBlockInfo2.state.getBlock() instanceof Waterloggable) {
            Chunk currentChunk = world.getChunk(currentChunkPos.x, currentChunkPos.z);
            if (world.getFluidState(structureBlockInfo2.pos).isIn(FluidTags.WATER)) {
                currentChunk.setBlockState(structureBlockInfo2.pos, structureBlockInfo2.state, false);
            }
        }
        return structureBlockInfo2;
    }

            @Override
    protected StructureProcessorType<?> getType() {
        return ProcessorRegistry.NO_WATERLOG_PROCESSOR;
    }
}
