#!/usr/bin/perl 

my $fileIN = $ARGV[0];
my $lines = "";
open (IN, $fileIN) || die "Can't open for read in!\n";
while (<IN>)
{
	chomp;
	$lines .= $_;
}
        
$command =  " grep -o \"SpecStr\" <<<\"$lines\" | wc -l ";
system ($command) ;




