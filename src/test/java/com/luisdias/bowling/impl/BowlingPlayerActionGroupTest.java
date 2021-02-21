package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingPlayerActionGroupTest {

    ActionValue strikeAction = new ActionValue(10, false);
    ActionValue foulAction = new ActionValue(0, true);
    ActionValue fivePinAction = new ActionValue(5, false);
    ActionValue threePinAction = new ActionValue(3, false);

    BowlingPlayerActionGroup actionGroup;

    @BeforeEach
    void setUp() {
        actionGroup = new BowlingPlayerActionGroup();
    }

    @DisplayName("addAction")
    @Nested
    class AddAction {

        @Test
        void shouldNotBeAbleToAddInvalidAction() {
            actionGroup.addAction(ActionValue.INVALID);

            assertThat(actionGroup.getValues()).isEmpty();
        }

        @Test
        void shouldBeAbleToAddActionIfNoPreviousActionAdded() {
            actionGroup.addAction(strikeAction);

            assertThat(actionGroup.getValues()).containsExactly(10);
        }

        @Test
        void shouldBeAbleToAddTwoActionsIfNoPreviousStrike() {
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.getValues()).containsExactly(5, 5);
        }

        @Test
        void shouldNotBeAbleToAddTwoActionsIfFirstWasStrike() {
            actionGroup.addAction(strikeAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.getValues()).containsExactly(10);
        }

        @Test
        void shouldNotBeAbleToAddThirdActionIfNotLastGroup() {
            actionGroup.addAction(strikeAction);
            actionGroup.addAction(strikeAction);
            actionGroup.addAction(strikeAction);

            assertThat(actionGroup.getValues()).containsExactly(10);
        }

        @Test
        void shouldBeAbleToAddThirdActionIfLastGroup() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(strikeAction);
            actionGroup.addAction(strikeAction);
            actionGroup.addAction(strikeAction);

            assertThat(actionGroup.getValues()).containsExactly(10, 10, 10);
        }

        @Test
        void shouldBeAbleToAddThirdActionIfLastGroupAndSpare() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.getValues()).containsExactly(5, 5, 5);
        }
    }

    @DisplayName("isComplete")
    @Nested
    class CompleteTests {

        @Test
        void shouldNotBeCompleteIfOnlyInvalidActions() {
            assertThat(actionGroup.isComplete()).isFalse();
        }

        @Test
        void shouldBeCompleteIfIsStrike() {
            actionGroup.addAction(strikeAction);

            assertThat(actionGroup.isComplete()).isTrue();
        }

        @Test
        void shouldBeCompleteIfIsSpare() {
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.isComplete()).isTrue();
        }

        @Test
        void shouldBeCompleteIfTwoActionsAndLessThanTenPoints() {
            actionGroup.addAction(threePinAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.isComplete()).isTrue();
        }

        @Test
        void shouldBeCompleteIfTwoFouls() {
            actionGroup.addAction(foulAction);
            actionGroup.addAction(foulAction);

            assertThat(actionGroup.isComplete()).isTrue();
        }

        @Test
        void shouldNotBeCompleteIfOnlyOneFoul() {
            actionGroup.addAction(foulAction);

            assertThat(actionGroup.isComplete()).isFalse();
        }

        @Test
        void shouldNotBeCompleteIfOnlyOneNormalAction() {
            actionGroup.addAction(threePinAction);

            assertThat(actionGroup.isComplete()).isFalse();
        }

        @Test
        void shouldBeCompleteIfLastGroupWithSpareAndBonusAction() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(strikeAction);

            assertThat(actionGroup.isComplete()).isTrue();
        }

        @Test
        void shouldBeCompleteIfLastGroupWithStrikeAndBonusAction() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(strikeAction);
            actionGroup.addAction(threePinAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.isComplete()).isTrue();
        }

        @Test
        void shouldBeCompleteIfLastGroupWithoutBonusAction() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(threePinAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.isComplete()).isTrue();
        }

        @Test
        void shouldNotBeCompleteIfLastGroupAndOnlyOneNormalAction() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(threePinAction);

            assertThat(actionGroup.isComplete()).isFalse();
        }
    }

    @DisplayName("foulsIndexes")
    @Nested
    class Fouls {

        int[] noFoulArray = new int[] { 0, 0 };
        int[] allFoulsArray = new int[] { 1, 1 };
        int[] firstActionFoulArray = new int[] { 1, 0 };
        int[] secondActionFoulArray = new int[] { 0, 1 };

        int[] lastNoFoulArray = new int[] { 0, 0, 0 };
        int[] lastAllFoulsArray = new int[] { 1, 1, 0 };
        int[] lastFirstActionFoulArray = new int[] { 1, 0, 0 };
        int[] lastSecondActionFoulArray = new int[] { 0, 1, 0 };
        int[] bonusActionFoulArray = new int[] { 0, 0, 1 };

        @Test
        void shouldReturnNoFoulArrayIfAllActionsAreInvalid() {
            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(noFoulArray);
        }

        @Test
        void shouldReturnAllFoulArrayIfAllActionsAreFouls() {
            actionGroup.addAction(foulAction);
            actionGroup.addAction(foulAction);

            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(allFoulsArray);
        }

        @Test
        void shouldReturnFirsFoulArrayIfFirstActionIsFoul() {
            actionGroup.addAction(foulAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(firstActionFoulArray);
        }

        @Test
        void shouldReturnSecondFoulArrayIfSecondActionIsFoul() {
            actionGroup.addAction(threePinAction);
            actionGroup.addAction(foulAction);

            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(secondActionFoulArray);
        }

        @Test
        void shouldReturnNoFoulArrayIfLastGroupAndNoFouls() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(strikeAction);

            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(lastNoFoulArray);
        }

        @Test
        void shouldReturnAllFoulArrayIfLastGroupAndAllActionsAreFouls() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(foulAction);
            actionGroup.addAction(foulAction);

            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(lastAllFoulsArray);
        }

        @Test
        void shouldReturnFirstFoulArrayIfLastGroupAndFirstActionIsFoul() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(foulAction);
            actionGroup.addAction(fivePinAction);

            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(lastFirstActionFoulArray);
        }

        @Test
        void shouldReturnSecondFoulArrayIfLastGroupAndSecondActionIsFoul() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(foulAction);

            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(lastSecondActionFoulArray);
        }

        @Test
        void shouldReturnBonusActionFoulArrayIfLastGroupAndLastActionIsFoul() {
            actionGroup.setAsLastGroup();
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(fivePinAction);
            actionGroup.addAction(foulAction);

            assertThat(actionGroup.getFoulIndexes())
                .isEqualTo(bonusActionFoulArray);
        }
    }
}