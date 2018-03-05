#!/usr/bin/env python
# coding:utf-8

import os
import shutil
import zipfile

class TextProcesser:
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

    def finish(self):
        with open(self.file_path, 'r') as src_file, open(self.file_path + '.new', 'w') as new_file:
            for line in src_file.readlines():
                need_write = True
                need_recreate = None
                for cmd in self.commands:
                    if cmd[0] == 'rm_line' and cmd[1] in line:
                        need_write = False
                        break

                    elif cmd[0] == 'rm_comment' and \
                            (line.startswith('/**') or line.startswith(' * ') or line.startswith(' */')):
                        need_write = False
                        break

                    elif cmd[0] == 'recreate':
                        need_recreate = cmd[1]
                        break

                    elif cmd[0] == 'replace':
                        line = line.replace(cmd[1], cmd[2])

                    elif cmd[0] == 'replace_header' and (line.startswith('package') or line.startswith('import')):
                        line = line.replace(cmd[1], cmd[2])

                if need_recreate is not None:
                    new_file.write(need_recreate)
                    break

                if need_write:
                    new_file.write(line)

        shutil.move(self.file_path + '.new', self.file_path)


class ProjectGenerator:

    def generate_project(self, project_name, package_name, old_project_name):
        # template_dir = 'app/'
        #
        # if os.path.exists(project_name):
        #     shutil.rmtree(project_name)
        # shutil.move(template_dir, project_name)
        #
        # os.chdir(project_name)
        # shutil.move('sample', 'app')

        print 'Creating project [%s]...' % project_name
        self.process(project_name, package_name, old_project_name)
        print 'Creat finished.'

    def process(self, project_name, package_name, old_project_name):
        # =================
        #       Root
        # =================
        # build.gradle
        if os.path.exists('build.gradle'):
            TextProcesser('build.gradle').rm_line_has_text('android-maven').finish()

        # rm unnessary files
        if os.path.exists('README.md'):
            os.remove('README.md')
        if os.path.exists('.gitattributes'):
            os.remove('.gitattributes')
        if os.path.exists('project_generator.py'):
            os.remove('project_generator.py')

        if os.path.exists('BaseAndroid.iml'):
            os.rename('BaseAndroid.iml', project_name + '.iml')

        # =================
        #       app
        # =================
        # build.gradle
        TextProcesser('app/build.gradle') \
            .replace_all_text(old_project_name, package_name) \
            .finish()

        # build.gradle
        TextProcesser('app/proguard-rules.pro') \
            .replace_all_text(old_project_name, package_name) \
            .finish()

        # AndroidManifest.xml
        TextProcesser('app/src/main/AndroidManifest.xml') \
            .replace_all_text(old_project_name, package_name) \
            .finish()

        # strings.xml
        TextProcesser('app/src/main/res/values/strings.xml') \
            .replace_all_text('Base Android Ominext', project_name) \
            .finish()

        # move package
        package_dir_postfix = package_name.replace('.', '/')
        package_old_dir_postfix = old_project_name.replace('.', '/')
        tmp_package_path = 'app/src/main/javaTmp/' + package_dir_postfix + '/'
        old_package_path = 'app/src/main/java/' + package_old_dir_postfix + '/'
        os.makedirs(tmp_package_path)

        #move all files to temp folder
        for f in os.listdir(old_package_path):
            shutil.move(old_package_path + f, tmp_package_path)
        shutil.rmtree('app/src/main/java')

        #change temp folder to local folder
        os.renames('app/src/main/javaTmp', 'app/src/main/java')

        new_package_path = 'app/src/main/java/' + package_dir_postfix + '/'

        # src files
        def process_all_src(path):
            for p in os.listdir(path):
                if os.path.isdir(path + p):
                    process_all_src(path + p + '/')

                elif p.endswith('.kt') or p.endswith('.java'):
                    TextProcesser(path + p) \
                        .replace_header(old_project_name, package_name) \
                        .finish()

        process_all_src(new_package_path)

        return self


def main():
    project_name = raw_input('Input new project name: ')
    old_project_name = raw_input('Input old package path (default enter): ')
    package_path = raw_input('Input the full package path (such as com.company.app): ')
    if not old_project_name:
        old_project_name = 'com.ominext.baseandroid'
    # template_zip, version = download_lastest_src()
    factory = ProjectGenerator()
    factory.generate_project(project_name, package_path, old_project_name)


if __name__ == '__main__':
    main()
