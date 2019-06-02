package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = Arrays.asList(
				new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
				new Ingredient("ALTO", "Alour Tortilla", Type.WRAP),
				new Ingredient("BLTO", "Blour Tortilla", Type.PROTEIN),
				new Ingredient("CLTO", "Clour Tortilla", Type.PROTEIN),
				new Ingredient("DLTO", "Dlour Tortilla", Type.VEGGIES),
				new Ingredient("ELTO", "Elour Tortilla", Type.VEGGIES),
				new Ingredient("GLTO", "Glour Tortilla", Type.CHEESE),
				new Ingredient("JLTO", "Jlour Tortilla", Type.CHEESE),
				new Ingredient("HLTO", "Hlour Tortilla", Type.SAUCE),
				new Ingredient("ILTO", "Ilour Tortilla", Type.SAUCE)
				);
		
		Type[] types = Ingredient.Type.values();
		
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		model.addAttribute("design", new Taco());
		return "design";
	}
	
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors) {
		if(errors.hasErrors()) {
			return "design";
		}
		
		log.info("Processing taco design: " + design);
		
		return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
		return ingredients.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
}
