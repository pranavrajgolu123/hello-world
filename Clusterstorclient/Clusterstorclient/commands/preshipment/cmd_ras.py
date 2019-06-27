import click
from Clusterstorclient.cli import pass_context
@click.group()
@click.option('--string', default='RAS', help='welcome to RAS')
@pass_context
def cli(ctx, string):
    "Current and historical system health alerts."
    pass

@cli.command('service_console')
@pass_context
def service_console():
    "Configure Service Console."
    click.echo("click from service-console")

@cli.group()
@pass_context
def support_bundle():
    "Manage support bundles and support bundle settings."
    pass

@support_bundle.command('collect')
@pass_context
def collect():
    "initiate (request) collection of the support bundle on specified set of nodes"
    click.echo("click from collect")

@support_bundle.command('delete')
@click.option('--bundle_id', help="the ID number of the support bundle file, which can be obtained from cscli support_bundle show -b")
@pass_context
def delete(bundle_id):
    "delete support bundle"
    click.echo("call from delete")

@support_bundle.command('export')
@click.option('--bundle_id', help="the ID number of the support bundle file, which can be obtained from cscli support_bundle show -b")
@pass_context
def export(bundle_id):
    "export support bundles as Tar.GZ archive (into the current folder)."
    click.echo("call from export")

@support_bundle.command('set')
@pass_context
def set():
    "support_bundle set command."
    click.echo("call from set")

@support_bundle.command('show')
@pass_context
def set():
    "support_bundle show command."
    click.echo("call from show")

@cli.command('syslog')
def syslog():
    "Retrieve syslog entries."
    click.echo("click from syslog")

@cli.group()
@pass_context
def monitor():
    "Monitoring current health of cluster nodes and elements."
    pass

@monitor.command("elements")
@pass_context
def elements():
    "Current status for elements"
    click.echo("click from elements")

@monitor.command("health")
@pass_context
def health():
    "Current overall health information - status summary."
    click.echo("click from health")

@monitor.command("nodes")
@pass_context
def nodes():
    "Current status for nodes."
    click.echo("click from nodes")







