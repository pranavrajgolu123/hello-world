import click
from Clusterstorclient.cli import pass_context
@click.group()
@click.option('--string',default='rack',help='welcome to rack')
@pass_context
def cli(ctx,string):
    pass

@cli.group()
def admin():
     pass

@cli.group()
def guest():
     pass

@cli.group()
def config():
     pass

@admin.command('show')


def show():
     "Admin Help"
     click.echo('call from admin')

@guest.command('show')


def show():
     "guest Help"
     click.echo('call from guest')



@config.command('show')


def show():
     "config Help"
     click.echo('call from config')



