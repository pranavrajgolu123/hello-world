import click
from Clusterstorclient.cli import pass_context
@click.group()
@click.option('--string',default='FRU',help='welcome to FRU')
@pass_context
def cli(ctx,string):
    pass

@cli.command('fru')
def fru():
    "Retrieve FRU information"
    click.echo("click from fru")


