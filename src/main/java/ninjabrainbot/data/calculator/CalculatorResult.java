package ninjabrainbot.data.calculator;

import java.util.ArrayList;
import java.util.List;

import ninjabrainbot.data.endereye.IThrow;
import ninjabrainbot.data.statistics.Posterior;
import ninjabrainbot.data.stronghold.Chunk;
import ninjabrainbot.data.stronghold.ChunkPrediction;
import ninjabrainbot.event.IObservable;
import ninjabrainbot.io.preferences.MultipleChoicePreferenceDataTypes.McVersion;
import ninjabrainbot.util.ISet;

public class CalculatorResult implements ICalculatorResult {

	private final ChunkPrediction bestPrediction;
	private final List<ChunkPrediction> topPredictions;

	public CalculatorResult() {
		bestPrediction = new ChunkPrediction();
		topPredictions = new ArrayList<ChunkPrediction>();
	}

	public CalculatorResult(Posterior posterior, ISet<IThrow> eyeThrows, IObservable<IThrow> playerPos, int numPredictions, McVersion version) {
		// Find chunk with largest posterior probability
		Chunk predictedChunk = posterior.getMostProbableChunk();
		bestPrediction = new ChunkPrediction(predictedChunk, playerPos, version);
		topPredictions = createTopPredictions(posterior, playerPos, numPredictions, version);
	}

	@Override
	public ChunkPrediction getBestPrediction() {
		return bestPrediction;
	}

	@Override
	public List<ChunkPrediction> getTopPredictions() {
		return topPredictions;
	}

	@Override
	public boolean success() {
		return bestPrediction.success;
	}

	private List<ChunkPrediction> createTopPredictions(Posterior posterior, IObservable<IThrow> playerPos, int amount, McVersion version) {
		List<ChunkPrediction> topPredictions = new ArrayList<ChunkPrediction>();
		List<Chunk> topChunks = posterior.getChunks();
		topChunks.sort((a, b) -> -Double.compare(a.weight, b.weight));
		for (Chunk c : topChunks) {
			topPredictions.add(new ChunkPrediction(c, playerPos, version));
			if (topPredictions.size() >= amount)
				break;
		}
		return topPredictions;
	}

	@Override
	public void dispose() {
		bestPrediction.dispose();
		if (topPredictions != null)
			for (ChunkPrediction p : topPredictions)
				p.dispose();
	}

}
