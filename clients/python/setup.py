import setuptools
with open("README.md", "r") as fh:
    long_description = fh.read()

setuptools.setup(
     name='arcusipcd',
     version='0.1',
     scripts=['dokr'] ,
     author="Andrew Sorensen",
     author_email="andrew@andrewsorensen.net",
     description="Arcus IPCD Client",
     long_description=long_description,
     long_description_content_type="text/markdown",
     url="https://github.com/arcus-smart-home/arcusipcd",
     packages=setuptools.find_packages(),
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