package monCollector.net;

import yuk.parser.DataParser;
import yuk.parser.inter.CommandInterface;

public class CollParser extends DataParser{

	public CollParser(CommandInterface agent, CommandInterface collector, CommandInterface server,
			CommandInterface console, CommandInterface etc) {
		super(agent, collector, server, console, etc);
	}

	
}
