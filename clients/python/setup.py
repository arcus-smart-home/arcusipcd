import setuptools

with open("README.md", "r") as fh:
    long_description = fh.read()

packages = ['ipcdclient']

setuptools.setup(
     name='ipcdclient',
     version='0.0.1',
     scripts=[] ,
     author="Andrew Sorensen",
     author_email="andrew@andrewsorensen.net",
     description="Arcus IPCD Client",
     long_description=long_description,
     long_description_content_type="text/markdown",
     url="https://github.com/arcus-smart-home/arcusipcd",
     packages=packages,
     package_dir={'': 'src'},
     install_requires=[
       'urllib3[secure]',
       'websockets',
       'certifi'
     ],
     classifiers=[
       'Intended Audience :: Developers',
       'License :: OSI Approved :: Apache Software License',
       'Operating System :: OS Independent',
       'Programming Language :: Python',
       'Programming Language :: Python :: 3',
       'Programming Language :: Python :: 3.6',
       'Programming Language :: Python :: 3.7'
     ],
 )
