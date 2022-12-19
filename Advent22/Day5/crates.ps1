$a = Get-Content .\input.txt 

$indexes = 1 ,5, 9, 13, 17, 21, 25, 29, 33
$i = 0
$id = 0
do
{
  if($i -eq 0)
{

$indexes | ForEach-Object {

Write-Host creating "stack$id"
New-Variable -Name "stack$id" -Force
$id++

$list = Get-Variable -Name "stack$id"
$list = $a[$i][$_]

Write-Host $list
}
}
$i++

}while($i -ne 8)