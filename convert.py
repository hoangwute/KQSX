import os
import shutil
import zipfile

class FileChangeDir:
    def __init__(self, file_path):
        self.file_path = file_path
        self.commands = []
    def rm_line_has_text(self, text):
        self.commands.append(('rm_line', text))
        return self

    def replace_all_text(self, src, dst):
        self.commands.append(('replace', src, dst))
        return self

    def replace_header(self, src, dst):
        self.commands.append(('replace_header', src, dst))
        return self

    def remove_comment(self):
        self.commands.append(('rm_comment', None))
        return self

    def recreate(self, text):
        self.commands = []
        self.commands.append(('recreate', text))
        return self
    def createFolder(self, text):

        pass
class FindAllFile:
    def process(self, pathFile):
        def processAllPath(path):
            print(path)
            for f in os.listdir(path):
                if(os.path.isdir(path + "\\" + f + "\\") and f.endswith('imageset')):
                    pathDir = path + "\\" + f + "\\"
                    pathXhdpi = "drawable-xhdpi\\"
                    pathXXhdpi ="drawable-xxhdpi\\"
                    pathXXXhdpi ="drawable-xxxhdpi\\"
                    if not os.path.exists(pathXhdpi):
                        os.makedirs(pathXhdpi)
                    if not os.path.exists(pathXXhdpi):
                        os.makedirs(pathXXhdpi)
                    if not os.path.exists(pathXXXhdpi):
                        os.makedirs(pathXXXhdpi)
                    for image in os.listdir(pathDir):
                        print(image)
                        if ".." in image:
                            rename(pathDir + image, pathDir + image.replace('..', '.'))
                        if(image.endswith('.png.png')):
                            rename(pathDir + image, pathDir + image.replace('.png.png', '.png').replace('-', '_'))
                            image = image.replace('.png.png', '.png')
                        if "@" not in image and image.endswith('.png'):
                            rename(pathDir + image, pathXhdpi + image.replace('-', '_'))
                        if(image.endswith('@2x.png')):
                            rename(pathDir + image, pathXXhdpi + image.replace('@2x.png', '.png').replace('-', '_'))
                        if(image.endswith('@3x.png')):
                            rename(pathDir + image, pathXXXhdpi + image.replace('@3x.png', '.png').replace('-', '_'))
                elif os.path.isdir(path + "\\" + f + "\\"):
                    processAllPath(path + "\\" + f + "\\")
        processAllPath(pathFile)
        def rename(oldPath, newPath):
            if not os.path.exists(newPath):
                os.rename(oldPath, newPath)
        return self
def main():
    fileFile = FindAllFile()
    path = os.path.dirname(os.path.realpath(__file__))
    fileFile.process(path)


if __name__ == '__main__':
    main()
