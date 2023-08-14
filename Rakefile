# frozen_string_literal: true

require 'time'

task default: [:push]

task :push do
  sh 'git add .'
  sh "git commit -m 'Update #{Time.now}.'"
  sh 'git push origin main'
end
