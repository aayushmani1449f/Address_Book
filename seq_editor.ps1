$content = Get-Content $args[0]
$content = $content -replace '^pick 66dc047', 'reword 66dc047'
$content | Set-Content $args[0]
