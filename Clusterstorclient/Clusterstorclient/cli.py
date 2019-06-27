import os
import sys,requests
import click


CONTEXT_SETTINGS = dict(auto_envvar_prefix='CSCLI')

class Context(object):

    def __init__(self):
        self.verbose = False
        self.home = os.getcwd()

    def log(self, msg, *args):
        """Logs a message to stderr."""
        if args:
            msg %= args
        click.echo(msg, file=sys.stderr)

    def vlog(self, msg, *args):
        """Logs a message to stderr only if verbose is enabled."""
        if self.verbose:
            self.log(msg, *args)


pass_context = click.make_pass_decorator(Context, ensure=True)

#response= requests.get('127.0.0.1:8080/cluster')
#response_data=response.json()
#mode = reponse_data['mode']
mode="preshipment"
if mode == 'preshipment':
   cmd_folder = os.path.abspath(os.path.join(os.path.dirname(__file__),
                                          'commands/preshipment'))
elif mode == 'custwiz':
   cmd_folder = os.path.abspath(os.path.join(os.path.dirname(__file__),
                                          'commands/custwiz'))
elif mode == 'daily':
   cmd_folder = os.path.abspath(os.path.join(os.path.dirname(__file__),
                                          'commands/daily'))

class ComplexCLI(click.MultiCommand):

    def list_commands(self, ctx):
        rv = []
        for filename in os.listdir(cmd_folder):
            if filename.endswith('.py') and \
               filename.startswith('cmd_'):
                rv.append(filename[4:-3])
        rv.sort()
        print rv
        return rv

    def get_command(self, ctx, name):
        try:
            if sys.version_info[0] == 2:
                name = name.encode('ascii', 'replace')
            if mode == 'preshipment':
               mod = __import__('Clusterstorclient.commands.preshipment.cmd_' + name,
                             None, None, ['cli'])
            elif mode == 'custwiz':
               mod = __import__('Clusterstorclient.commands.custwiz.cmd_' + name,
                             None, None, ['cli'])
            elif mode == 'daily':
               mod = __import__('Clusterstorclient.commands.cmd_' + name,
                             None, None, ['cli'])
        except ImportError:
            return
        return mod.cli


@click.command(cls=ComplexCLI, context_settings=CONTEXT_SETTINGS)
@click.option('--home', type=click.Path(exists=True, file_okay=False,
                                        resolve_path=True),
              help='Changes the folder to operate on.')
@click.option('-v', '--verbose', is_flag=True,
              help='Enables verbose mode.')
@pass_context
def cli(ctx, verbose, home):
    """A complex command line interface."""
    ctx.verbose = verbose
    if home is not None:
        ctx.home = home
