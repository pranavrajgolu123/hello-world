from setuptools import setup

setup(
    name='Clusterstorclient',
    version='0.1',
    packages=['Clusterstorclient', 'Clusterstorclient.commands','Clusterstorclient.common', \
              'Clusterstorclient.commands.preshipment', 'Clusterstorclient.commands.daily', 'Clusterstorclient.commands.custwiz'],
    include_package_data=True,
    install_requires=[
        'click',
    ],
    entry_points='''
        [console_scripts]
        cscli=Clusterstorclient.cli:cli
    ''',
)
