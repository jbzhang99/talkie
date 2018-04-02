package com.meta.sync.syncconvert.terminal;

import com.meta.model.terminal.Terminal;
import com.meta.sync.model.terminal.SyncTerminal;
import org.springframework.stereotype.Component;


@Component
public class SyncConvertTerminal {
    public SyncTerminal convertTerminal(Terminal terminal) {
        if(null == terminal) {
            return null;
        }
        SyncTerminal syncTerminal = new SyncTerminal();
        syncTerminal.setId(terminal.getId());
        return syncTerminal;
    }

}
