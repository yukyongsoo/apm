package monServer.net.stoper;

import yuk.parser.DataParser;
import yuk.parser.inter.CommandInterface;

public class MonConsoleDataParser extends DataParser{

	public MonConsoleDataParser(CommandInterface agent,
			CommandInterface collector, CommandInterface server,
			CommandInterface console, CommandInterface etc) {
		super(agent, collector, server, console, etc);
		
	}

}
