module.exports = {
  '{src/**/,}*.ts': ['eslint --fix', 'prettier --write'],
  '*.{md,json*,yml,html,css,scss,java,feature}': ['prettier --write'],
};
