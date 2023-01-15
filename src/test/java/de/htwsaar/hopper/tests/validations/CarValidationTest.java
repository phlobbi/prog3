package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;
import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import static org.junit.Assert.*;

public class CarValidationTest {

	@Test
	public void carTypeIsAuto() {
		CarTypeEnum type = CarTypeEnum.AUTO;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsLKW() {
		CarTypeEnum type = CarTypeEnum.LKW;
		Validation.validateCarType(type);

	}

	@Test
	public void carTypeIsMotorrad() {
		CarTypeEnum type = CarTypeEnum.MOTORRAD;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsBus() {
		CarTypeEnum type = CarTypeEnum.BUS;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsLimousine() {
		CarTypeEnum type = CarTypeEnum.LIMOUSINE;
		Validation.validateCarType(type);
	}


	@Test
	public void carTypeIsSuv() {
		CarTypeEnum type = CarTypeEnum.SUV;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsVan() {
		CarTypeEnum type = CarTypeEnum.VAN;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsCabrio() {
		CarTypeEnum type = CarTypeEnum.CABRIO;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsKombi() {
		CarTypeEnum type = CarTypeEnum.KOMBI;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsCoupe() {
		CarTypeEnum type = CarTypeEnum.COUPE;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsSportwagen() {
		CarTypeEnum type = CarTypeEnum.SPORTWAGEN;
		Validation.validateCarType(type);
	}

	@Test
	public void carTypeIsAndere() {
		CarTypeEnum type = CarTypeEnum.ANDERE;
		Validation.validateCarType(type);
	}
}
