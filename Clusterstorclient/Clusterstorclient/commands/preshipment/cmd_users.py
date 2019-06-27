"""
this module 
author: praj@cray.com
"""
import click
import requests
from Clusterstorclient.cli import pass_context
from Clusterstorclient.common.util import cli_to_uri, dict_to_tab, dict_to_one
from tabulate import tabulate


@click.group()
@click.option('--string', default='user2', help='welcome to user2')
@pass_context
def cli(ctx, string):
    """cscli users"""
    pass
# cscli users admins
@cli.group()
def admins():
    "Manage admin accounts."
    pass
@admins.command('add')
@click.option('--enable_ssh/--disable_ssh', default=True, help=''' \
		Enables/Disables SSH for the specified admin account (default:SSH enabled).''')
@click.option('--enable_web/--disable_web', default=True, help=''' \
        Enables/Disables web access for the specified admin account(default: web enabled).''')
@click.option('--username', required=True, help='Specifies the name of the user.')
@click.option('--firstname', help='Specifies the users first name.')
@click.option('--lastname', help='Specifies the users last name.')
@click.option('--password_policy', help='''\
                Creates the user account with the specified password policy.Specifying this will take \
                precedence over'--stream-api'.if not specified, then \
                'default'passwor policywill be used.''')
@click.option('--role', required=True, help='''\
                Displays available roles; Select one from the list:{full, limited, readonly}.''')
@click.option('--password', prompt=True, confirmation_prompt=True, hide_input=True, \
                help='Sets the password of the specified admin account.')
@click.pass_context
def add(ctx, role, password_policy, password, \
        username, lastname, firstname, enable_web, enable_ssh):
    "Creates a new admin account."
    click.echo('Hello %s!' % username)
    click.echo('call from add')
@admins.command('list')
def list():
    """Displays all the available admin accounts, except default admin account, namely {admin}"""
    #uri = cli_to_uri('127.0.0.1', 8080, ['users', 'admins'])
    resp1 = requests.get('http://127.0.0.1:5000/star')
    data = resp1.json()
    click.echo(dict_to_tab(data))
@admins.command('show')
@click.option('--username', required=True, help='Specifies the name of the user.')
@click.pass_context
def show(ctx, username):
    "Displays details of the specified admin account."
    click.echo('call from show')
    #uri = cli_to_uri('127.0.0.1', 8080, ['users', 'admins', username])
    resp = requests.get('http://127.0.0.1:5000/star' + '/' + username)
    data=resp.json()
    click.echo(dict_to_one(data))
    
@admins.command('remove')
@click.option('--username', required=True, help='Specifies the name of the user.')
def remove():
    "Removes the specified admin account."
    click.echo('call from show')
    uri = cli_to_uri('127.0.0.1', 8080, ['users', 'admins', username])
    if stream == True:
        click.echo(uri)
    else:
        resp = requests.get(uri)
    dict = {'output': [{'a':1, 'b':2}, {'a':5, 'b':10}],\
            'out': [{'a':11, 'b':12}, {'a':15, 'b':20}]}
    click.echo(dict_to_tab(dict))

@admins.command('modify')
@click.option('--username', required=True, help='Specifies the name of the user.')
@click.option('--new_firstname', help='Specifies a new first name.')
@click.option('--new_lastname', help='Specifies a new last name.')
@click.option('--new_role', help='''\
                Displays available roles; Select one from the list:{full, limited, readonly}.''')
@click.option('--new_shell', help='''\
                Displays available shells; Select one from the list:{bash, rcsh}.''')
def modify(ctx, username, old_firstname, new_lastname, new_shell,\
           new_role):
    "Modifies preferences for the specified admin account."
    click.echo('call from modify')

@admins.command('reset_password')
@click.option('--username', required=True, help='Specifies the name of the user.')
@click.option('--old_password', help='Specifies your old password.')
@click.option('--new_password', help='Specifies your new password.')
def rest_password(username, old_password, new_password):
    "Resets the password for the specified admin account."
    click.echo('call from reset_password')

@admins.command('enable')
@click.option('--username', required=True, help='Specifies the name of the user.')
def enable():
    "Enables the specified admin account."
    click.echo('call from enable')

@admins.command('disable')
@click.option('--username', required=True, help='Specifies the name of the user.')
def disable():
    "Disables the specified admin account"
    click.echo('call from disable')

#admins policy command
@admins.group()
def policy():
    'policy group'
    pass

@policy.command('list')
def list():
    "Enables the specified admin account."
    click.echo('call from list')

@policy.command('show')
def show():
    "Enables the specified admin account."
    click.echo('call from show')

@policy.command('add')
def add():
    "Enables the specified admin account."
    click.echo('call from add')

@policy.command('set')
def set():
    "Enables the specified admin account."
    click.echo('call from set')

@policy.command('remove')
def remove():
    "Enables the specified admin account."
    click.echo('call from remove')

# cscli users guests
@cli.group()
def guest():
    pass

#guest command
@guest.command('show')
def show():
    "guest Help"
    click.echo('call from guest')

# cscli users configs
@cli.group()
def config():
    pass

#config command
@config.command('show')
def show():
    "config Help"
    click.echo('call from config')
