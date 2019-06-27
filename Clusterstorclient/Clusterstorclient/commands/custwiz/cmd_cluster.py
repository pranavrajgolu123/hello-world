import click
import requests, tabulate, json
from Clusterstorclient.cli import pass_context
from Clusterstorclient.common.util import cli_to_uri, dict_to_tab, dict_to_one, cluster_timezone, cluster_setdate,cluster_mode
@click.group()
@click.option('--string',default='cluster',help='welcome to cluster')
@pass_context
def cli(ctx,string):
    pass

@cli.command('set_date')
def set_date():
    "Manage cluster date."
    click.echo(cluster_setdate())

@cli.command('set_timezone')
@click.option('--show', is_flag=True, help='show the date')
@click.option('--list', is_flag=True, help='list the timezone')
@click.option('--set', help='Enter the timzone')
def set_timezone(show, set, list):
    """Manage cluster timezone."""
    if (show, list, set):
        click.echo("It is not a option")
    elif show:
       #click.echo(cluster_timezone(show))
       click.echo('cluster_timezone()')
    elif list:
       #click.echo(cluster_timezone(list))
       click.echo('cluster_timezone(show)')

    elif set:
       click.echo(cluster_timezone(set))

    else:
       click.echo("--show  show the date " +"\n"+  "--set  Enter the timezone")

@cli.command('show_update_version')
def show_update_version():
    "Show the available versions in the Management Server repository"
    click.echo("click from show_update_version")

@cli.command('autodiscovery_mode')
def autodiscovery_mode():
    "Enable/Disable nodes auto-discovery."
    click.echo("click from autodiscovery_mode")

@cli.command('csinfo')
def csinfo():
    "Generate yaml file with Cluster information."
    click.echo("click from csinfo")

@cli.command('cluster_mode')
@click.option('-s', is_flag=True, help='show the status')
@click.option('--status', is_flag=True, help='show the status')
@click.option('--db_only', is_flag=True, help='show the db_only')
@click.option('--mode', help='Enter the mode')
def cluster_mode(s, status, db_only, mode):
    "Switch cluster into the 'daily mode' or back into the 'custWizard', 'pre-shipment'. "
    if s:
        cluster_mode(s)

    elif status:
        cluster_mode(status)

    elif mode or (mode and db_only):
        cluster_mode(mode, db_only)

    else:
        click.echo('-s  show the status\n'+ '--status  show the status\n'+'--mode Enter the mode'+'--db_only  ')



