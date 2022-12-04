. ..\common.ps1

$score = 0
$alphabet = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
$a = Get-Content .\input.txt 
Write-Host $a.Length

$count = 0

do
{
$group = $a[$count],$a[$count+1], $a[$count+2]
Write-Host group is 
Write-Host $group
$match = Find-Matches $group
$score += $alphabet.IndexOf($match)
$count += 3

Write-Host count is $count
}while($count -ne $a.Length)

Write-Host $score