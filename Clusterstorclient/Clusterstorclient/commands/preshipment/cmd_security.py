import click
import requests, tabulate, json
from Clusterstorclient.cli import pass_context
from Clusterstorclient.common.util import cli_to_uri, dict_to_tab, dict_to_one

headers = {
        'Content-Type': 'application/json',
    }
def send(port):
    payload = {
        "operation": "update",
        "port": port

    }
    uri = cli_to_uri(['security', 'ssh_port'])
    req_put = requests.post(uri, headers=headers, data=json.dumps(payload))
    response = req_put.json()
    data = sorted([(k,v) for k,v in response.items()])
    return(tabulate(data, tablefmt="grid"))

@click.group()
@click.option('--string', default='user', help='welcome to user')
@pass_context
def cli(ctx, string):
    pass

@cli.group()
def ssh_port():
    """SSH tcp port settings."""
    pass

@ssh_port.command('apply')
def apply():
    """Stop listening to backup port"""
    payload = {
        "operation": "apply",

    }
    uri = cli_to_uri(['security', 'ssh_port'])
    req_put = requests.post(uri, headers=headers, data=json.dumps(payload))
    response = req_put.json()
    data = sorted([(k, v) for k, v in response.items()])
    return (tabulate(data, tablefmt="grid"))

@ssh_port.command('clear')
def clear():
    """Disable SSH port redirection, return SSH to default port (22)"""
    send(22)

@ssh_port.command('set')
@click.option('--port', required = True, help='Enter port no')
def set(port):
    """Assign new port to SSH, leave existing one as a backup"""
    send(port)

@ssh_port.command('show')
def show():
    """Show current SSH port status"""
    uri = cli_to_uri(['security', 'ssh_port'])
    response = requests.get(uri)
    return dict_to_tab(response)

@cli.group()
def ssl():
    """Manage SSL certificate."""
    pass

@ssl.command('install')
def install():
    """Manage SSL certificate."""
    click.echo("click from install")
@ssl.command('show')
def show():
    """Show currently installed certificate"""
    click.echo("click from show")











