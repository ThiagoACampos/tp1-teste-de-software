package test.testedesoftware.clinicacovid.unitTests;

import testedesoftware.clinicacovid.model.Equipment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {
  private Equipment equipment;

  @BeforeEach
  void setUp() {
    equipment = new Equipment("Seringa", 10, true);
  }

  @Test
  void testCanUse() {
    assertTrue(equipment.canUse());
  }

  @Test
  void testCannotUse() {
    equipment.setStatus(false);
    assertFalse(equipment.canUse());
  }

  @Test
  void testUseNonConsumableEquipment() throws Exception {
    equipment.setStatus(false);
    Exception exception = assertThrows(Exception.class, () -> {
      equipment.use();
    });
    assertEquals(equipment.exceptionEquipmentNoLongerAvailableOrCannotBeUsed, exception.getMessage());
  }

  @Test
  void testUseConsumableEquipmentSuccess() {
    equipment.use();
    assertEquals(9, equipment.getQuantity());
  }

  @Test
  void testUseConsumableEquipmentFailure() throws Exception {
    final Exception exception = assertThrows(Exception.class, () -> {
      for(int i = 0; i < 20; i++){
        equipment.use();
      }
    });

    assertEquals(
      equipment.exceptionEquipmentNoLongerAvailableOrCannotBeUsed,
      exception.getMessage()
    );
  }

  @Test
  void testAddUnits() {
    equipment.addUnits(21);
    assertEquals(31, equipment.getQuantity());
  }

  @Test
  void testAddAndUseUnitsUntilSpent() {
    equipment.addUnits(21);
    assertEquals(31, equipment.getQuantity());
    for(int i = 0; i < 31; i++){
      equipment.use();
    }
    assertEquals(0, equipment.getQuantity());
  }

  @Test
  void testUseAtMaxQuantity() throws Exception {
    // Test if setting quantity to max.
    equipment.setQuantity(equipment.getMax());
    for(int i = 0; i < equipment.getMax(); i++) {
      equipment.use();
    }
    assertEquals(0, equipment.getQuantity());
    // Makes the equipment unusable after max uses.
    assertFalse(equipment.canUse());
  }

  /*
   * The only way to subtract equipment quantity should be
   * through the .use() function
   */
  @Test
  void testAddNegativeQuantity() throws Exception {
    // A user should not be able to add a negative quantity.
    final Exception exceptionAddNegativeQuantity = assertThrows(Exception.class, () -> {
      equipment.addUnits(-1);
    });
    assertEquals(
      equipment.exceptionEquipmentQuantityMustBePositive,
      exceptionAddNegativeQuantity.getMessage()
    );
    // And the quantity must remain the same after the operation fails.
    assertEquals(10, equipment.getQuantity());
  }

  @Test
  void testSetNegativeQuantity() throws Exception {
    // A user should not be able to set a negative quantity.
    final Exception exceptionSetNegativeQuantity = assertThrows(Exception.class, () -> {
      equipment.setQuantity(-1);
    });
    assertEquals(
      equipment.exceptionEquipmentQuantityMustBePositive,
      exceptionSetNegativeQuantity.getMessage()
    );
    // And the quantity must remain the same after the operation fails.
    assertEquals(10, equipment.getQuantity());
  }

  @Test
  void testCheckQuantityAtMaximum() {
    // The maximum number of units for a given equipment is 100.
    equipment.setQuantity(100);
    assertEquals(100, equipment.getQuantity());

    equipment.setQuantity(1);
    equipment.addUnits(99);
    assertEquals(100, equipment.getQuantity());
  }

  @Test
  void testAddQuantityAboveMaximum() throws Exception {
    // If a user tries to add a quantity that exceeds the maximum of 100.
    final Exception exceptionAddUnitsAboveMaximum = assertThrows(Exception.class, () -> {
      equipment.addUnits(91);
    });
    // An exception must be thrown.
    assertEquals(
      equipment.exceptionEquipmentQuantityExceedsMaximum,
      exceptionAddUnitsAboveMaximum.getMessage()
    );
    // And the equipment quantity must remain the same.
    assertEquals(10, equipment.getQuantity());
  }

  @Test
  void testSetQuantityAboveMaximum() throws Exception {
    // If a user tries to Set a quantity that exceeds the maximum of 100.
    final Exception exceptionSetUnitsAboveMaximum = assertThrows(Exception.class, () -> {
      equipment.setQuantity(101);
    });
    // An exception must be thrown.
    assertEquals(
      equipment.exceptionEquipmentQuantityExceedsMaximum,
      exceptionSetUnitsAboveMaximum.getMessage()
    );
    // And the equipment quantity must remain the same.
    assertEquals(10, equipment.getQuantity());
  }

  @Test
  void testSetQuantityAndAddQuantityThatExceedsMaximum() throws Exception {
    equipment.setQuantity(50);
    final Exception exception = assertThrows(Exception.class, () -> {
      equipment.addUnits(51);
    });
    assertEquals(equipment.exceptionEquipmentQuantityExceedsMaximum, exception.getMessage());
    // Quantity should remain unchanged.
    assertEquals(50, equipment.getQuantity());
  }
}
