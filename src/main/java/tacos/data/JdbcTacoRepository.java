package tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;
import tacos.Taco;
import tacos.web.IngredientByIdConverter;

@Repository
public class JdbcTacoRepository
	implements TacoRepository {
	private JdbcTemplate jdbc;
	
	private IngredientByIdConverter ibic;
	private IngredientRepository ir;
	
	@Autowired
	public JdbcTacoRepository(JdbcTemplate jdbc, IngredientRepository ir) {
		this.jdbc = jdbc;
		this.ir = ir;
		ibic = new IngredientByIdConverter(this.ir);
	}

	@Override
	public Taco save(Taco taco) {
		
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		
		for (String ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ibic.convert(ingredient), tacoId);
		}
		
		return taco;
	}

	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update(
				"insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
				tacoId,
				ingredient.getId());
	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values(?, ?)",
				Types.VARCHAR, Types.TIMESTAMP);
		
		PreparedStatementCreator psc = 
				pscf
				.newPreparedStatementCreator(
						Arrays.asList(taco.getName(), 
								new Timestamp(taco.getCreatedAt().getTime()))
						);
		
		pscf.setReturnGeneratedKeys(true);
		
		System.out.println(taco.getCreatedAt().getTime());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		System.out.println(keyHolder.getKey());
		return keyHolder.getKey().longValue();
	}
	
	
}
