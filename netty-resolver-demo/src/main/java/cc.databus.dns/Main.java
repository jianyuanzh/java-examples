package cc.databus.dns;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.resolver.ResolvedAddressTypes;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsNameResolverBuilder;
import io.netty.resolver.dns.SequentialDnsServerAddressStreamProvider;
import io.netty.util.internal.SocketUtils;
import org.apache.commons.cli.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) throws ParseException {

        Options options = new Options()
                .addOption("t", "type", true, "IPV4_ONLY, IPV6_ONLY, IPV4_PREFERRED or IPV6_PREFERRED")
                .addOption("m", "maxQueriesPerResolve", true, "the maximum allowed number of DNS queries for a given name resolution")
                .addOption("s", "dns-servers", true, "DNS servers")
                .addOption("T", "timeout", true, "timeout of each DNS query in millis")
                .addOption("a", "resolveAll", false, "add this option means resolve all ips")
                .addOption("d", "dns", true, "hostnames to be resolved")
                .addOption("h", "help", false, "show help info");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (args.length == 0 || cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "resolve [optinos]", options );
            return;
        }



        NioEventLoopGroup loopGroup = new NioEventLoopGroup(1);
        DnsNameResolverBuilder builder = new DnsNameResolverBuilder(loopGroup.next())
                .channelType(NioDatagramChannel.class)
                .resolvedAddressTypes(ResolvedAddressTypes.valueOf(cmd.getOptionValue("t", "IPV4_PREFERRED").toUpperCase()))
                .maxQueriesPerResolve(Integer.parseInt(cmd.getOptionValue("m", "16")))
                .queryTimeoutMillis(Long.parseLong(cmd.getOptionValue("T", "5000")));

        String serversStr = cmd.getOptionValue("s", "");
        if (serversStr != null && !serversStr.isEmpty()) {
            List<String> serverList = new ArrayList<>();
            Collections.addAll(serverList, serversStr.split(","));
            List<InetSocketAddress> addresses =
                    serverList.stream().map(server -> SocketUtils.socketAddress(server, 53)).collect(toList());
            builder.nameServerProvider(new SequentialDnsServerAddressStreamProvider(addresses));
        }


        DnsNameResolver resolver = builder.build();

        String[] hostnames = cmd.getOptionValues("d");

        boolean resolveAll = cmd.hasOption("a");
        try {
            for (String host : hostnames) {
                doResolve(resolver, host, resolveAll);
            }
        }
        finally {
            loopGroup.shutdownGracefully();
        }

    }

    private static void doResolve(final DnsNameResolver resolver, String hostname, boolean all) {
        if (all) {
            resolveAll(resolver, hostname);
        }
        else {
            resolve(resolver, hostname);
        }
    }

    private static void resolve(final DnsNameResolver resolver, String hostname) {
        long start = System.currentTimeMillis();
        long elapsed = 0;
        try {
            InetAddress address = resolver.resolve(hostname).get();
            elapsed = System.currentTimeMillis() - start;
            System.out.println("--Resolve done: " + address);
        }
        catch (Exception e) {
            elapsed = System.currentTimeMillis() - start;
            System.out.println("--Resolve failed: " + e);
        }
        finally {
            System.out.println("Elapsed: " + (elapsed) + "ms");
        }
    }

    private static void resolveAll(final DnsNameResolver resolver, String hostname) {
        long start = System.currentTimeMillis();
        long elapsed = 0;
        try {
            List<InetAddress> addresses = resolver.resolveAll(hostname).get();
            elapsed = System.currentTimeMillis() - start;
            System.out.println("--Resolve done: " + addresses);
        }
        catch (Exception e) {
            elapsed = System.currentTimeMillis() - start;
            System.out.println("--Resolve failed: " + e);
        }
        finally {
            System.out.println("Elapsed: " + elapsed + "ms");
        }
    }
}
