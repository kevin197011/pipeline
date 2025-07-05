# frozen_string_literal: true

# Copyright (c) 2025 kk
#
# This software is released under the MIT License.
# https://opensource.org/licenses/MIT

require 'time'

task default: %w[push]

task :push do
  system <<-SHELL
    rubocop -A
    git update-index --chmod=+x push
    git add .
    git commit -m "Update #{Time.now}"
    git pull
    git push origin main
  SHELL
end
