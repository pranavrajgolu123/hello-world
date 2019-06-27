import click
from Clusterstorclient.cli import pass_context
@click.group()
@click.option('--string',default='nodes',help='welcome to nodes')
@pass_context
def cli(ctx,string):
    pass

@cli.command("configure_hosts")
def configure_hosts():
    "Configure hostname for discovered node."
    click.echo("click from Configure_hosts")

@cli.command("configure_mds")
def configure_mds():
    "Configure new MDS node"
    click.echo("click from configure_mds")

@cli.command("configure_nodes")
def configure_nodes():
    "Configure new OSS node."
    click.echo("click from configure_nodes")

@cli.command("create_filter")
def create_filter():
    "Create customer nodes filter"
    click.echo("click from create_filter")

@cli.command("delete_filter")
def delete_filter():
    "Delete customer nodes filter."
    click.echo("click from delete_filter")

@cli.command("remove_unit")
def remove_unit():
    "Remove unit(s) from cluster."
    click.echo("click from remove_unit")

@cli.command("restore_mgmt")
def restore_mgmt():
    "Enable mgmt node recovery. When enabled, mgmt node that boots is restored from the latest good backup. "
    click.echo("click from restore_mgmt")

@cli.command("show_filters")
def show_filters():
    "Show nodes filters."
    click.echo("click from show_filters")

@cli.command("show_new_nodes")
def show_new_nodes():
    "Show table with new OSS nodes and it resources."
    click.echo("click from show_new_nodes")

@cli.command("show_node_version")
def show_node_version():
    "Show version(s) for the specified nodes."
    click.echo("click from show_node_version")

@cli.command("show_nodes")
def show_nodes():
    "Display information about nodes"
    click.echo("click from show_nodes")

@cli.command("show_version_nodes")
def show_version_nodes():
    "Show nodes at the specified version."
    click.echo("click from show_version_nodes")

@cli.command("update_node")
def update_node():
    "Update specified node."
    click.echo("click from update_node")

@cli.command("set_node_version")
def set_node_version():
    "Change image for the diskless node(s)."
    click.echo("click from set_node_version")















