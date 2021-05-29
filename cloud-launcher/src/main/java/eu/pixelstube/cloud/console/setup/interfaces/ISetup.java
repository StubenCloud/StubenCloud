package eu.pixelstube.cloud.console.setup.interfaces;

/*

  » de.thundercloud.launcher.console.setup.interfaces

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 31.03.2021 / 11:52

 */

import eu.pixelstube.cloud.console.setup.SetupBuilder;
import eu.pixelstube.cloud.console.setup.abstracts.SetupEnd;
import eu.pixelstube.cloud.console.setup.abstracts.SetupInput;

public abstract class ISetup {

    private SetupInput[] setupInputs;
    private SetupInput currentInput;
    private SetupEnd setupEnd;

    public void setSetupInputs(SetupInput... setupInputs){
        this.setupInputs = setupInputs;
        this.currentInput = setupInputs[0];

        new SetupBuilder(this, setupEnd, setupInputs);
    }

    public void setSetupEnd(SetupEnd setupEnd){
        this.setupEnd = setupEnd;
    }

    public void setCurrentInput(SetupInput currentInput) {
        this.currentInput = currentInput;
    }

    public SetupEnd getSetupEnd() {
        return setupEnd;
    }

    public SetupInput[] getSetupInputs() {
        return setupInputs;
    }

    public SetupInput getCurrentInput() {
        return currentInput;
    }
}
