rem Create and initialize a Python Virtual Environment
echo "Creating the virtual environment - .venv"
python -m venv .venv

echo "starting the virtual environment - .venv"
call .venv\Scripts\activate.bat

rem Create a directory to put things in
echo "Creating 'setup' directory"
mkdir setup

rem Move the relevant files into setup directory
echo "Moving function file(s) to setup dir"
xcopy cuckoo.py setup\ /Q /R /Y
cd .\setup

rem Install requirements 
echo "pip installing requirements from requirements file in target directory"
pip install -r ..\requirements.txt -t .

echo "Zipping package"
tar.exe -a -cf ../package.zip *

rem Remove the setup directory used
echo "Removing setup directory and virtual environment"
cd ..
rd /Q /S .\setup
call .venv\Scripts\deactivate.bat
rd /Q /S .\.venv

rem changing dirs back to dir from before
echo "Opening folder containg function package - 'package.zip'"
explorer .