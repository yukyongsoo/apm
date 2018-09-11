package monServer.net;

import yuk.parser.DataParser;
import yuk.parser.inter.CommandInterface;

public class ServerDataParser extends DataParser{

	public ServerDataParser(CommandInterface agent, CommandInterface collector, CommandInterface server,
			CommandInterface console, CommandInterface etc) {
		super(agent, collector, server, console, etc);
	}
}
