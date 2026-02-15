/*

Running bochs in debugging mode:
bochsi-2021 -q -f ~/.bochsrc_debug

mkdir -p ~/lib
ln -s /lib64/libreadline.so.8 ~/lib/libreadline.so.6
echo '# Fix for bochs libreadline.so.6' >> ~/.bashrc
echo 'export LD_LIBRARY_PATH=~/lib:$LD_LIBRARY_PATH' >> ~/.bashrc
source ~/.bashrc
*/
