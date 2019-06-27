import click
from Clusterstorclient.cli import pass_context
@click.group()
@click.option('--string', default='RAS', help='welcome to RAS')
@pass_context
def cli(ctx,string):
    pass

@cli.group()
def elements():
    "ekkk"
    pass

@elements.command('show_a')
def show_a():
    " Stop listening to backup port"
    click.echo("call from show_a")

@elements.command('show')
def show():
    " Stop listening to backup port"
    click.echo("call from show")

@cli.group()
def nodes():
    "dhd"
    pass

@nodes.command('show')
def show():
    " Stop listening to backup port"
    click.echo("call from show")

@nodes.command('show_a')
def show_a():
    " Stop listening to backup port"
    click.echo("call from show_a")







     
