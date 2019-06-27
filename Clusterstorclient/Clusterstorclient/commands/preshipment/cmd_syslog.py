import click
from Clusterstorclient.cli import pass_context

@click.group()
@click.option('--string',default='syslog',help='welcome to syslog')
@pass_context
def cli(ctx,string):
    pass

@cli.command('syslog')
def syslog():
    "Retrieve syslog entries."
    click.echo("click from syslog")








