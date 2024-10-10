package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsTest() {
        String expectedName = "TastyBun";
        float expectedPrice = 15f;

        Bun bun = Mockito.mock(Bun.class);
        Mockito.when(bun.getName()).thenReturn(expectedName);
        Mockito.when(bun.getPrice()).thenReturn(expectedPrice);

        burger.setBuns(bun);

        assertEquals(expectedName, burger.bun.getName());
        assertEquals(expectedPrice, burger.bun.getPrice(), 0.0001);
    }

    @Test
    public void addIngredientTest() {
        IngredientType expectedIngredientType = IngredientType.FILLING;
        String expectedName = "Meat";
        float expectedPrice = 40f;

        Ingredient ingredient = Mockito.mock(Ingredient.class);
        Mockito.when(ingredient.getType()).thenReturn(expectedIngredientType);
        Mockito.when(ingredient.getName()).thenReturn(expectedName);
        Mockito.when(ingredient.getPrice()).thenReturn(expectedPrice);

        burger.addIngredient(ingredient);

        Ingredient actualIngredient = burger.ingredients.get(0);
        assertEquals(expectedIngredientType, actualIngredient.getType());
        assertEquals(expectedName, actualIngredient.getName());
        assertEquals(expectedPrice, actualIngredient.getPrice(), 0.0001);
    }

    @Test
    public void removeIngredientTest() {
        Ingredient ingredient = Mockito.mock(Ingredient.class);
        assertEquals(0, burger.ingredients.size());
        burger.addIngredient(ingredient);
        assertEquals(1, burger.ingredients.size());
        burger.removeIngredient(0);
        assertEquals(0, burger.ingredients.size());
    }

    @Test
    public void moveIngredientTest() {
        Ingredient ingredient1 = Mockito.mock(Ingredient.class);
        Ingredient ingredient2 = Mockito.mock(Ingredient.class);

        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        assertEquals(ingredient1, burger.ingredients.get(0));
        assertEquals(ingredient2, burger.ingredients.get(1));

        burger.moveIngredient(0,1);

        assertEquals(ingredient1, burger.ingredients.get(1));
        assertEquals(ingredient2, burger.ingredients.get(0));
    }

    @Test
    public void getPriceOnlyBunsBurgerTest() {
        float expectedPrice = 15f;
        Bun bun = Mockito.mock(Bun.class);
        Mockito.when(bun.getPrice()).thenReturn(expectedPrice);

        burger.setBuns(bun);

        assertEquals(expectedPrice * 2, burger.getPrice(), 0.0001);
    }

    @Test
    public void getPriceFullBurgerTest() {
        float expectedBunPrice = 15f;
        float expectedIngredient1Price = 40f;
        float expectedIngredient2Price = 10f;
        Bun bun = Mockito.mock(Bun.class);
        Mockito.when(bun.getPrice()).thenReturn(expectedBunPrice);

        Ingredient ingredient1 = Mockito.mock(Ingredient.class);
        Mockito.when(ingredient1.getPrice()).thenReturn(expectedIngredient1Price);

        Ingredient ingredient2 = Mockito.mock(Ingredient.class);
        Mockito.when(ingredient2.getPrice()).thenReturn(expectedIngredient2Price);

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        assertEquals(expectedBunPrice * 2 + expectedIngredient1Price + expectedIngredient2Price,
                burger.getPrice(), 0.0001);
    }

    @Test
    public void getReceiptOnlyBunsBurgerTest() {
        String expectedName = "TastyBun";
        float expectedPrice = 15f;
        Bun bun = Mockito.mock(Bun.class);
        Mockito.when(bun.getName()).thenReturn(expectedName);
        Mockito.when(bun.getPrice()).thenReturn(expectedPrice);

        burger.setBuns(bun);

        String expectedReceipt = "(==== " + expectedName + " ====)\n(==== " + expectedName + " ====)\n\nPrice: "
                + String.format("%f", expectedPrice * 2) + "\n";
        assertEquals(expectedReceipt, burger.getReceipt());
    }

    @Test
    public void getReceiptFullBurgerTest() {
        String expectedBunName = "TastyBun";
        float expectedBunPrice = 15f;

        IngredientType expectedIngredient1Type = IngredientType.FILLING;
        String expectedIngredient1Name = "Meat";
        float expectedIngredient1Price = 40f;

        IngredientType expectedIngredient2Type = IngredientType.SAUCE;
        String expectedIngredient2Name = "HotSauce";
        float expectedIngredient2Price = 10f;

        Bun bun = Mockito.mock(Bun.class);
        Mockito.when(bun.getName()).thenReturn(expectedBunName);
        Mockito.when(bun.getPrice()).thenReturn(expectedBunPrice);

        Ingredient ingredient1 = Mockito.mock(Ingredient.class);
        Mockito.when(ingredient1.getType()).thenReturn(expectedIngredient1Type);
        Mockito.when(ingredient1.getName()).thenReturn(expectedIngredient1Name);
        Mockito.when(ingredient1.getPrice()).thenReturn(expectedIngredient1Price);

        Ingredient ingredient2 = Mockito.mock(Ingredient.class);
        Mockito.when(ingredient2.getType()).thenReturn(expectedIngredient2Type);
        Mockito.when(ingredient2.getName()).thenReturn(expectedIngredient2Name);
        Mockito.when(ingredient2.getPrice()).thenReturn(expectedIngredient2Price);

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        String expectedReceipt = "(==== " + expectedBunName + " ====)\n= "
                + String.format("%s %s", expectedIngredient1Type.toString().toLowerCase(), expectedIngredient1Name)
                + " =\n= "
                + String.format("%s %s", expectedIngredient2Type.toString().toLowerCase(), expectedIngredient2Name)
                + " =\n(==== " + expectedBunName + " ====)\n\nPrice: "
                + String.format("%f", expectedBunPrice * 2 + expectedIngredient1Price + expectedIngredient2Price)
                + "\n";
        assertEquals(expectedReceipt, burger.getReceipt());
    }
}