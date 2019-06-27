import click
import requests, tabulate, json
from Clusterstorclient.cli import pass_context
from Clusterstorclient.common.util import cli_to_uri, dict_to_tab, dict_to_one, cluster_mode
@click.group()
@click.option('--string',default='cluster',help='welcome to cluster')
@pass_context
def cli(ctx,string):
    pass

headers = {
        'Content-Type': 'application/json',
    }
def cluster_timezone_set(set):
    payload = {
        "timezone": set
    }
    if set != None:
        uri = cli_to_uri(['cluster', 'timezone'])
        req_put = requests.put(uri, headers=headers, data=json.dumps(payload))
        if req_put.status_code == 200:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
        elif req_put.status_code == 401:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))


@cli.command('set_date')
def set_date():
    "Manage cluster date."


@cli.command('set_timezone')
@click.option('--show', is_flag=True, help='show the date')
@click.option('--list', is_flag=True, help='list the timezone')
@click.option('--set', help='Enter the timzone')
def set_timezone(show, set, list):
    """Manage cluster timezone."""
    if show and list and set:
        click.echo("It is not a option")

    elif show and list:
        click.echo("It is not a option")

    elif show and set:
        click.echo("It is not a option")

    elif list  and set:
        click.echo("It is not a option")

    elif show:
        uri = cli_to_uri(['cluster', 'timezone'])
        req_put = requests.get(uri)
        if req_put.status_code == 200:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
        elif req_put.status_code == 401:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))

    elif list:
        uri = cli_to_uri(['cluster', 'timezone', 'availaible'])
        req_put = requests.get(uri)
        if req_put.status_code == 200:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
        elif req_put.status_code == 401:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))

    elif set:
        cluster_timezone_set(set)

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



