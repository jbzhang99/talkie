package com.meta.convert.terminal;

import com.meta.sync.model.terminal.SyncTerminal;
import com.tcloud.talkie.module.terminal.vo.TerminalVO;
import org.springframework.stereotype.Component;

@Component
public class ConvertTerminal {

    public TerminalVO convertNew2Old(SyncTerminal syncTerminal) {
        if (null == syncTerminal) {
            return null;
        }

        TerminalVO oldTerminal = new TerminalVO();

        oldTerminal.setId(syncTerminal.getId());

        return oldTerminal;
    }
}
