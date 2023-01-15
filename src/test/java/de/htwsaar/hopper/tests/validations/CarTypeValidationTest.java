package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.CarValidation;
import org.junit.Test;
import de.htwsaar.hopper.logic.enums.CarTypeEnum;

public class CarTypeValidationTest {

	@Test
	public void carTypeIsAuto() {
		CarTypeEnum type = CarTypeEnum.AUTO;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsLKW() {
		CarTypeEnum type = CarTypeEnum.LKW;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsMotorrad() {
		CarTypeEnum type = CarTypeEnum.MOTORRAD;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsBus() {
		CarTypeEnum type = CarTypeEnum.BUS;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsLimousine() {
		CarTypeEnum type = CarTypeEnum.LIMOUSINE;
		CarValidation.validateCarType(type);
	}


	@Test
	public void carTypeIsSuv() {
		CarTypeEnum type = CarTypeEnum.SUV;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsVan() {
		CarTypeEnum type = CarTypeEnum.VAN;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsCabrio() {
		CarTypeEnum type = CarTypeEnum.CABRIO;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsKombi() {
		CarTypeEnum type = CarTypeEnum.KOMBI;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsCoupe() {
		CarTypeEnum type = CarTypeEnum.COUPE;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsSportwagen() {
		CarTypeEnum type = CarTypeEnum.SPORTWAGEN;
		CarValidation.validateCarType(type);
	}

	@Test
	public void carTypeIsAndere() {
		CarTypeEnum type = CarTypeEnum.ANDERE;
		CarValidation.validateCarType(type);
	}

	@Test(expected = IllegalArgumentException.class)
	public void carTypeIsNull() {
		CarValidation.validateCarType(null);
	}
}
