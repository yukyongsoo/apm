package monui.impl.net;

import yuk.parser.DataParser;
import yuk.parser.inter.CommandInterface;

public class UiParser extends DataParser{
	public UiParser(CommandInterface agent, CommandInterface collector, CommandInterface server,CommandInterface console, CommandInterface etc) {
		super(agent, collector, server, console, etc);
	}
}
