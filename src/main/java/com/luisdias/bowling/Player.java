package com.luisdias.bowling;

import java.util.List;

public interface Player {

    String getName();

    boolean doAction(ActionValue value);

    List<PlayerActionGroup> getCompletedActionGroups();
}
