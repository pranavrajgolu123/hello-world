import click
from Clusterstorclient.cli import pass_context
@click.group()
@click.option('--string',default='network',help='welcome to network')
@pass_context
def cli(ctx,string):
    pass

@cli.command('show')
def show():
    "Show network configuration of the cluster."
    click.echo("click from show")

@cli.command('smctl')
def smctl():
    "Manage Infiniband Subnet Manager."
    click.echo("click from smctl")

@cli.command('netfilter')
def netfilter():
    "Netfilter level management."
    click.echo("click from netfilter")

@cli.command('data_network')
def data_network():
    "Data network IP addresses management."
    click.echo("click from data_network")

@cli.group()
def range():
    ""
    pass

@range.command('data')
def data():
    "Add new range of IPs into Data Network."
    click.echo("click from data")

@range.command('extend')
def extend():
    "Extend range of IPs for Data Network."
    click.echo("click from range")

@range.command('find')
def find():
    "Show unused IPs in ranges of Data Network."
    click.echo("click from find")


@range.command('show')
def show():
    "Shows the network pairing information(ip and lhostname) of data_network of the cluster."
    click.echo("click from show")

@range.command('list')
def list():
    "List ranges of IPs for Data Network."
    click.echo("click from list")

@range.command('remove')
def remove():
    "Remove range of IPs for Data Network"
    click.echo("click from remove")

@range.command('apply')
def apply():
    "Apply network configuration to cluster "
    click.echo("click from apply")

@range.group()
def settings():
    "Manage netmask and MTU for Data Network."
    pass

@settings.command('set')
def set():
    "Set netmask and MTU for Data Network IP configuration."
    click.echo("click from set")

@settings.command('show')
def show():
    "View the current netmask and MTU for Data Network IP addresses"
    click.echo("click from show")

@cli.command('ip_routing')
def ip_routing():
    "IP routing management."
    click.echo("click from ip_routing")

@cli.group()
def ean():
    "Configuration of the External Administration Network (EAN)."
    pass

@ean.command('apply')
def apply():
    "Apply EAN configuration changes."
    click.echo("click from apply")

@ean.group()
def dns():
    "DNS configuration. Configuring DNS on the EAN will provide DNS for the entire cluster."
    pass

@dns.command('clear')
def clear():
    "Clear DNS servers."
    click.echo("click from clear")

@dns.command('set')
def clear():
    "Set DNS servers."
    click.echo("click from set")

