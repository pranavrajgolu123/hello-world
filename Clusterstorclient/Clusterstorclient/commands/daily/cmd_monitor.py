import click
from Clusterstorclient.cli import pass_context
@click.group()
@click.option('--string',default='monitor',help='welcome to monitor')
@pass_context
def cli(ctx,string):
    pass

@cli.group()
def monitor():
    "Monitoring current health of cluster nodes and elements."
    pass

@monitor.command('elements')
def elements():
    " Current status for elements."
    click.echo("click from elements")

@monitor.command('health')
def elements():
    "Current overall health information - status summary."
    click.echo("click from health")

@monitor.command('nodes')
def elements():
    "Current status for nodes."
    click.echo("click from nodes")


